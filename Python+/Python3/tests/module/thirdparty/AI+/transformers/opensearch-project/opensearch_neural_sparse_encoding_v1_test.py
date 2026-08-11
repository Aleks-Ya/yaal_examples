import itertools

import torch
from transformers import AutoModelForMaskedLM, AutoTokenizer


# https://huggingface.co/opensearch-project/opensearch-neural-sparse-encoding-v1
def test_use_model():
    # get sparse vector from dense vectors with shape batch_size * seq_len * vocab_size
    def get_sparse_vector(feature, output):
        values, _ = torch.max(output * feature["attention_mask"].unsqueeze(-1), dim=1)
        values = torch.log(1 + torch.relu(values))
        values[:, special_token_ids] = 0
        return values

    # transform the sparse vector to a dict of (token, weight)
    def transform_sparse_vector_to_dict(sparse_vector):
        sample_indices, token_indices = torch.nonzero(sparse_vector, as_tuple=True)
        non_zero_values = sparse_vector[(sample_indices, token_indices)].tolist()
        number_of_tokens_for_each_sample = torch.bincount(sample_indices).cpu().tolist()
        tokens = [transform_sparse_vector_to_dict.id_to_token[_id] for _id in token_indices.tolist()]

        output = []
        end_idxs = list(itertools.accumulate([0] + number_of_tokens_for_each_sample))
        for i in range(len(end_idxs) - 1):
            token_strings = tokens[end_idxs[i]:end_idxs[i + 1]]
            weights = non_zero_values[end_idxs[i]:end_idxs[i + 1]]
            output.append(dict(zip(token_strings, weights)))
        return output

    # load the model
    model = AutoModelForMaskedLM.from_pretrained("opensearch-project/opensearch-neural-sparse-encoding-v1")
    tokenizer = AutoTokenizer.from_pretrained("opensearch-project/opensearch-neural-sparse-encoding-v1")

    # set the special tokens and id_to_token transform for post-process
    special_token_ids = [tokenizer.vocab[token] for token in tokenizer.special_tokens_map.values()]
    get_sparse_vector.special_token_ids = special_token_ids
    id_to_token = ["" for i in range(tokenizer.vocab_size)]
    for token, _id in tokenizer.vocab.items():
        id_to_token[_id] = token
    transform_sparse_vector_to_dict.id_to_token = id_to_token

    query = "What's the weather in ny now?"
    document = "Currently New York is rainy."

    # encode the query & document
    feature = tokenizer([query, document], padding=True, truncation=True, return_tensors='pt',
                        return_token_type_ids=False)
    output = model(**feature)[0]
    sparse_vector = get_sparse_vector(feature, output)

    # get similarity score
    sim_score = torch.matmul(sparse_vector[0], sparse_vector[1])
    print(sim_score)  # tensor(22.3299, grad_fn=<DotBackward0>)

    query_token_weight, document_query_token_weight = transform_sparse_vector_to_dict(sparse_vector)
    for token in sorted(query_token_weight, key=lambda x: query_token_weight[x], reverse=True):
        if token in document_query_token_weight:
            print("score in query: %.4f, score in document: %.4f, token: %s" % (
            query_token_weight[token], document_query_token_weight[token], token))

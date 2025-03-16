package pytorch;

import org.junit.jupiter.api.Test;
import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.util.Arrays;

class PyTorchTest {
    @Test
    void test() {
        var module = Module.load("model.pt");
        var inputData = new float[]{1.0f, 2.0f, 3.0f, 4.0f};
        var inputTensor = Tensor.fromBlob(inputData, new long[]{1, inputData.length});
        var outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        var outputData = outputTensor.getDataAsFloatArray();
        System.out.println("Model output: " + Arrays.toString(outputData));
    }
}

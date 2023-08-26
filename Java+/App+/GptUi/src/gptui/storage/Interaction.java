package gptui.storage;

public record Interaction(String theme,
                          String question,
                          String askForQuestionCorrectness,
                          String questionCorrectnessAnswer,
                          String askForShortAnswer,
                          String shortAnswer,
                          String askForLongAnswer,
                          String longAnswer) {
}

package gptui.fxml;

import gptui.storage.Answer;
import gptui.storage.AnswerType;
import gptui.storage.Interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {
    private List<Interaction> interactionHistory = new ArrayList<>();
    private Interaction currentInteraction;
    private List<String> themeList;
    private String currentTheme;
}

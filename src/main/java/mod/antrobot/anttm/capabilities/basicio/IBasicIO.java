package mod.antrobot.anttm.capabilities.basicio;

public interface IBasicIO {
    String input = "null";
    String output = "null";

    String getInput();
    void setInput(String i);

    String getOutput();
    void setOutput(String i);

    int calculateInput(int slot);
    int calculateOutput(int slot);

    boolean isValid(int slot);
}

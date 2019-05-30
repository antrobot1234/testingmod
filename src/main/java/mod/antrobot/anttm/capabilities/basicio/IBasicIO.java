package mod.antrobot.anttm.capabilities.basicio;

public interface IBasicIO {
    String input = "null";
    boolean output = false;

    String getInput();
    void setInput(String i);

    boolean getOutput();
    void setOutput(boolean i);

    int calculateInput(int slot);
    int calculateOutput(int slot);

    boolean isValid(int slot);
}

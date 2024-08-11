package com.hg.product.utility.patterns;

import org.w3c.dom.Text;

public class CommandPatternOperation {
    public static void main(String[] args) {
        CommandPatternOperation cpo = new CommandPatternOperation();

        Command copy = new CopyCommand();
        Command paste = new PasteCommand();

        TextBox textBox = new TextBox();
        textBox.copy(copy);
        textBox.paste(paste);
    }
}

interface Command {
    boolean execute();
}

class CopyCommand implements Command {
    @Override
    public boolean execute() {
        System.out.println("copied to history");
        return true;
    }
}

class PasteCommand implements Command {
    @Override
    public boolean execute() {
        System.out.println("pasted to history");
        return true;
    }
}

class TextBox {
    public void copy(Command command) {
        command.execute();
        System.out.println("text box, copied");
    }
    public void paste(Command command) {
        command.execute();
        System.out.println("text box, pasted");
    }
}

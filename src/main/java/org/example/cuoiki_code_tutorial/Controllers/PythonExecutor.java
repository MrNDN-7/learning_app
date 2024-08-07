package org.example.cuoiki_code_tutorial.Controllers;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.fxmisc.richtext.CodeArea;
import org.python.util.PythonInterpreter;
import org.python.core.*;

import java.io.StringWriter;

public class PythonExecutor {
    private final PythonInterpreter interpreter;
    private final CodeArea codeArea;
    private final TextArea outputArea;

    public PythonExecutor(CodeArea codeArea, TextArea outputArea) {
        this.codeArea = codeArea;
        this.outputArea = outputArea;
        System.setProperty("python.import.site", "false");
        this.interpreter = new PythonInterpreter();
    }

    public String executeCode(String testcase) {
        String code = codeArea.getText();

        String input = code + testcase;
        String output = "hello world";
        try {
            StringWriter writer = new StringWriter();
            interpreter.setOut(writer);
            interpreter.exec(input);
            output = writer.toString();
        } catch (Exception e) {

            e.printStackTrace();
            output = e.getMessage();
        }
        return output;
    }


}
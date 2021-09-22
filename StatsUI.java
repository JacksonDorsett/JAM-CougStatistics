import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.BorderLayout;

class GroupedPanel extends JPanel {
    GroupLayout layout;

    ParallelGroup labelGroup;
    ParallelGroup textFieldGroup;

    SequentialGroup stackedGroup;

    GroupedPanel() {
        layout = new GroupLayout(this);
        setLayout(layout);

        labelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        textFieldGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        stackedGroup = layout.createSequentialGroup();

        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(labelGroup).addGroup(textFieldGroup));
        layout.setVerticalGroup(stackedGroup);
    }

    void addPair(JLabel label, JTextField textField) {
        labelGroup.addComponent(label);
        textFieldGroup.addComponent(textField);

        stackedGroup.addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label).addComponent(textField));
    }
}

class ErrorPane extends JPanel {
    static final String BaseText = "Errors:\n";
    JTextArea textArea;

    ErrorPane() {
        super(new BorderLayout());
        textArea = new JTextArea(BaseText);
        JScrollPane scroll = new JScrollPane(textArea);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll ,BorderLayout.CENTER);
        add(new JButton("Clear"){{
            addActionListener(new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent e ){
                    textArea.setText(BaseText);
                    System.out.println("hi!");
                }
            });
        }},BorderLayout.SOUTH);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
    }

    void log(String error) {
        System.out.println(error);
        textArea.append(error + "\n");
    }
}

public class StatsUI {
    HashMap<String, Dataset> inputs = new HashMap<>();
    HashMap<String, String> outpus = new HashMap<>();
    HashMap<String, Set<Runnable>> inputDependents = new HashMap<>();

    JFrame frame;
    GroupedPanel inputPanel;
    GroupedPanel outputPanel;
    ErrorPane errorPane;

    int width;
    int height;

    StatsUI() {
        this("Coug Stats",500,500);
    }
    StatsUI(int width, int height) {
        this("Coug Stats", width, height);
    }

    StatsUI(String title) {
        this(title, 500,500);
    }

    StatsUI(String title, int width, int height) {
        this.width = width;
        this.height = height;

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane contentPanes = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane errorSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
 
        inputPanel = new GroupedPanel();

        outputPanel = new GroupedPanel();
        errorPane = new ErrorPane();



       contentPanes.add(inputPanel);
        contentPanes.add(outputPanel);

        errorSplit.add(contentPanes);
        errorSplit.add(errorPane);
        contentPanes.setDividerLocation(0.5);
        errorSplit.setDividerLocation(0.8);
        frame.add(errorSplit);
    }

    void updateInputs(String input, String inputId) {
        updateInputs(input, inputId, null);
    }

    void updateInputs(String input, String inputId, Integer tupleSize) {
        try {
            if (input.trim().length() == 0) {
                inputs.remove(inputId);
            } else {
                Dataset data = tupleSize == null ? Dataset.parse(input) : Dataset.parse(input, tupleSize);
                inputs.put(inputId, data);
                Set<Runnable> dependents = inputDependents.get(inputId);
                if (dependents != null) {
                    for (Runnable updateDependent : dependents) {
                        updateDependent.run();
                    }
                }
            }
        } catch (Exception e) {
            errorPane.log(e.getMessage());
        }
    }

    void addInput(String label, String inputId) {
        addInput(label, inputId, null);
    }

    void addInput(String label, String inputId, Integer tupleSize) {
        JTextField textField = new JTextField(20);
        Action setInputAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInputs(textField.getText(), inputId, tupleSize);
            }
        };
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                updateInputs(textField.getText(), inputId, tupleSize);
            }
        };
        textField.addActionListener(setInputAction);
        textField.addFocusListener(focusListener);
        inputPanel.addPair(new JLabel(label), textField);
        inputPanel.validate();
        frame.validate();
    }

    void addOutput(String label, String[] dependencies, Function<Dataset[], String> getOutput) {
        JTextField textField = new JTextField(20);
        textField.setEditable(false);
        for (String inputId : dependencies) {
            Set<Runnable> otherDependents = inputDependents.containsKey(inputId) ? inputDependents.get(inputId)
                    : new HashSet<>();
            otherDependents.add(() -> {
                Dataset[] datasets = new Dataset[dependencies.length];
                for (int i = 0; i < datasets.length; i++) {
                    String dependency = dependencies[i];
                    datasets[i] = inputs.get(dependency);
                }
                System.out.printf("%s noticed the update!\n", label);
                try {
                    textField.setText(getOutput.apply(datasets));
                } catch (Exception e) {
                    System.out.println("Throuble in paradise!");
                    textField.setText(e.getMessage());
                }
            });
            inputDependents.put(inputId, otherDependents);
        }
        outputPanel.addPair(new JLabel(label), textField);
        
        outputPanel.validate();
        frame.validate();
    }

    void run() {
        frame.pack();
        frame.setVisible(true);
    }
}

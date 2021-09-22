import java.util.List;
import java.util.ArrayList;

public class UIDemo {
    

    public static void main(String[] args) {
        StatsUI ui = new StatsUI("Averaging grades");
        // Creating the inputs
        // Note: Right now, tuples of size 1 must be represented as (n)
        // i.e. a dataset containing the numebrs 1, 2, and 3 would be the string "(1),(2),(3)"
        // And, as a list: [[1],[2],[3]]
        List<String> inputs = new ArrayList<>();
        for(int i =0; i < 10;i++){
            inputs.add(""+i);
            ui.addInput("Student "+i+"'s grades:",""+i, 1);
        }

        // Averaging the provided grades (assuming nice data)
        ui.addOutput("Average",inputs.stream().toArray(String[]::new),(students)->{
            int numGrades = students[0].data.size();
            List<List<Float>> result = new ArrayList<>();
            for(int i = 0; i < numGrades;i++){
                float addedGrade = 0;
                for(Dataset student: students){
                    addedGrade+=student.data.get(i).get(0);
                }
                final float average = addedGrade/students.length;
                result.add(new ArrayList<Float>(){{add(average);}});
            }
            return result.toString();
        });
        ui.run();
    }
}

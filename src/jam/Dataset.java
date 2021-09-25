import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class DatasetParseException extends Exception {
    static final Pattern parenPattern = Pattern.compile("\\)\\s*\\(");
    static final String baseErrorString = "Error while parsing dataset";

    DatasetParseException(String message) {
        super(message);
    }

    DatasetParseException(String invalidInput, int startIndex) {
        super(getErrorMessage(invalidInput, startIndex));
    }

    DatasetParseException(Exception exception, int startIndex) {
        super(getErrorMessage(exception, startIndex), exception);
    }

    static String getErrorMessage(Exception exception, int startIndex) {
        String message = exception.getMessage();
        int index = message.indexOf('"');
        return index == -1 ? message : getErrorMessage(message.substring(index + 1, message.length() - 1), startIndex);
    }

    static String getErrorMessage(String invalidInput, int startIndex) {
        startIndex += 1;
        String baseString = baseErrorString + " near index " + startIndex + "; ";

        if (invalidInput.equals("")) {
            return baseString + "No closing paren found";
        }

        if (invalidInput.startsWith("(")) {
            return baseString + "Open paren before close paren found";
        }

        if (invalidInput.endsWith(")")) {
            return baseString + "Close paren before open paren found";
        }
        Matcher parenResult = parenPattern.matcher(invalidInput);
        if (parenResult.find()) {
            int parenIndex = startIndex + parenResult.start();
            return baseString + "It seems a comma is missing at index " + parenIndex + "'" + invalidInput + "'";
        } else if (invalidInput.indexOf('.') != invalidInput.lastIndexOf('.')) {
            return baseString + "Multiple decimal points in the same value" + "'" + invalidInput + "'";
        }
        return baseString + "'" + invalidInput + "'";
    }
}

// A list of tuples
class Dataset {
    List<List<Float>> data;

    Dataset(List<List<Float>> data) {
        this.data = data;
    }
    Dataset() {
        this(new ArrayList<List<Float>>());
    }

    // Note: Right now, tuples of size 1 must be represented as (n)
    // i.e. a dataset containing the numebrs 1, 2, and 3 would be the string "(1),(2),(3)"
    // And, as a list: [[1],[2],[3]]
    static Dataset parse(String input, int tupleSize) throws DatasetParseException {
        Dataset dataset = parse(input);
        //List<List<Float>> wrongSizeTuples = dataset.data.stream().filter(tuple -> tuple.size() != tupleSize).toList();
        if (wrongSizeTuples.size() > 0) {
            String tupleString = "";
            String isAre = "";
            String data = "";
            if (wrongSizeTuples.size() == 1) {
                tupleString = "Tuple";
                isAre = "is";
                data = wrongSizeTuples.get(0).toString();
            } else {
                tupleString = "Tuples";
                isAre = "are";
                //data = wrongSizeTuples.stream().map(tuple -> tuple.toString()).toList().toString();
            }
            throw new DatasetParseException(
                    tupleString + " " + data + " " + isAre
                            + " not of size " + tupleSize);
        }
        return dataset;
    }
    static List<Dataset> parseFile(String path) throws DatasetParseException {
        File file = new File(path);
        List<Dataset> datasets = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                tuples.add(Dataset.parse(line));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
        }
        return datasets;
    }

    static Dataset parse(String input) throws DatasetParseException {
        input = input.trim();
        List<List<Float>> allPoints = new ArrayList<>();
        List<Float> currentPoint = null;

        if (input.length() == 0) {
            return new Dataset(allPoints);
        }
        String[] splitInput = input.split(",");
        int stringIndex = 0;
        for (int splitIndex = 0; splitIndex < splitInput.length; splitIndex++) {
            String currentString = splitInput[splitIndex];
            String floatString = currentString;
            boolean pointFinished = false;
            if (floatString.startsWith("(")) {
                if (currentPoint == null) {
                    currentPoint = new ArrayList<>();
                    floatString = floatString.substring(1);
                } else {
                    throw new DatasetParseException(floatString, stringIndex);
                }
            }
            if (floatString.endsWith(")")) {
                if (currentPoint == null) {
                    throw new DatasetParseException(floatString, stringIndex);
                } else {
                    pointFinished = true;
                    floatString = floatString.substring(0, floatString.length() - 1);
                }
            }
            try {
                float value = Float.parseFloat(floatString);

                if (currentPoint != null) {
                    currentPoint.add(value);
                }
            } catch (NumberFormatException e) {
                throw new DatasetParseException(e, stringIndex);
            }
            if (pointFinished) {
                allPoints.add(currentPoint);
                currentPoint = null;
            }
            stringIndex += currentString.length();
        }
        if (currentPoint != null) {
            throw new DatasetParseException("", input.length());
        }
        return new Dataset(allPoints);
    }

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter datapoints: ");
            String input = inp.nextLine();
            if (input.equals("")) {
                break;
            }
            try {
                System.out.println(parse(input, 3).data);
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        inp.close();

    }
}
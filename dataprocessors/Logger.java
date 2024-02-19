package dataprocessors;
import datatypes.Data;

public class Logger extends DataProcessor {
    @Override
    protected Data process(Data input) {
        System.out.println(input.toString());
        return null;
    }
}

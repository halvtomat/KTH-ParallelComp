package skiplists;
import java.util.ArrayList;
import java.util.Collections;

public class Recorder<T> {

    private ArrayList<Sample<T>> samples = new ArrayList<Sample<T>>();

    public void addSample(int method, T argument, boolean status) {
        Sample<T> sample = new Sample<T>(method, argument, status);
        samples.add(sample);
    }
    public void addSample(long linearizationTime, int method, T argument, boolean status) {
        Sample<T> sample = new Sample<T>(linearizationTime, method, argument, status);
        samples.add(sample);
    }
    public void addSample(Sample<T> sample) {
        samples.add(sample);
    }

    public void sort() {
        Collections.sort(samples);
    }

    public boolean validate() {
        Sample<T> s1;
        Sample<T> s2;
        for(int i = 0; i < samples.size()-1; i++) {
            s1 = samples.get(i);
            for (int j = i+1; j < samples.size(); j++) {
                s2 = samples.get(j);
                if(s1 == null || s2 == null)
                    continue;
                if(s1.getArgument() == s2.getArgument()) {
                    if(!validSamples(s1, s2)){
                        s1.printSample();
                        s2.printSample();
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    private boolean validSamples(Sample<T> s1, Sample<T> s2) {
        if(s1.getArgument() != s2.getArgument())
            return true;
        else {
            Sample<T> controlSample1;
            Sample<T> controlSample2;
            if(s1.getCurrentTime() < s2.getCurrentTime()){
                controlSample1 = s1;
                controlSample2 = s2;
            } else {
                controlSample1 = s2;
                controlSample2 = s1;
            }
            switch(controlSample1.getMethod()) {
                case 0: // contains
                    if(controlSample2.getMethod() == 1)
                        return controlSample1.getStatus() != controlSample2.getStatus();
                    return controlSample1.getStatus() == controlSample2.getStatus();
                case 1: // add
                    if(controlSample2.getMethod() == 1)
                        return !controlSample2.getStatus();
                    return controlSample2.getStatus();
                default: // remove
                    if(controlSample2.getMethod() == 1)
                        return controlSample2.getStatus();
                    return !controlSample2.getStatus();
            }
        }
    }

    public void printSamples() {
        for(Sample<T> s : samples) {
            s.printSample();
        }
    }

    public ArrayList<Sample<T>> getSamples() {
        return this.samples;
    }
}

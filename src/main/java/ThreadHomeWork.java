import java.util.Arrays;

public class ThreadHomeWork {
    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public static void secondMethod() {

        final int size = 10_000_000;
        final int HALF_SIZE = size / 2;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        float[] mergedArray = new float[size];

        long startTime = System.currentTimeMillis();

        float[] leftHalf = new float[HALF_SIZE];
        float[] rightHalf = new float[HALF_SIZE];
        System.arraycopy(arr, 0, leftHalf, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, rightHalf, 0, HALF_SIZE);
        Thread leftHalfTransfer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < leftHalf.length; i++) {
                    leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(leftHalf, 0, mergedArray, 0, leftHalf.length);
            }
        });

        Thread rightHalfTransfer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < rightHalf.length; i++) {
                    rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                System.arraycopy(rightHalf, 0, mergedArray, HALF_SIZE, rightHalf.length);
            }
        });

        boolean compare = Arrays.equals(leftHalf, rightHalf);

        leftHalfTransfer.start();
        rightHalfTransfer.start();
        try {
            leftHalfTransfer.join();
            rightHalfTransfer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(compare);
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }


}

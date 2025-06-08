public class BitwiseMatchingPattern {
    // Method to find the next greater number with the same number of binary 1s
    public static int getNext(int n) {
        int temp = n;
        int zeroCount = 0, oneCount = 0;

        // Count trailing 0s
        while ((temp & 1) == 0 && temp != 0) {
            zeroCount++;
            temp >>= 1;
        }

        // Count 1s after trailing 0s
        while ((temp & 1) == 1) {
            oneCount++;
            temp >>= 1;
        }

        // Edge case: If no higher number is possible
        if (zeroCount + oneCount == 31 || zeroCount + oneCount == 0) {
            return -1;
        }

        // Position to change the bit
        int position = zeroCount + oneCount;

        // Step 1: Set the bit at 'position' to 1
        n |= (1 << position);

        // Step 2: Clear all bits to the right of 'position'
        n &= ~((1 << position) - 1);

        // Step 3: Add (oneCount - 1) 1s to the rightmost bits
        n |= (1 << (oneCount - 1)) - 1;

        return n;
    }

    // Test cases to check the result
    public static void main(String[] args) {
        int n1 = 5;   // Binary: 101 → Next: 6 (110)
        int n2 = 6;   // Binary: 110 → Next: 9 (1001)
        int n3 = 7;   // Binary: 111 → Next: 11 (1011)

        System.out.println("Input: " + n1 + " → Output: " + getNext(n1));
        System.out.println("Input: " + n2 + " → Output: " + getNext(n2));
        System.out.println("Input: " + n3 + " → Output: " + getNext(n3));
    }
}

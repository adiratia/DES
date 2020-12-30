class DES {
    private String[] keys = new String[16];
    private static int[] PC1 =
            {
                    57, 49, 41, 33, 25, 17, 9,
                    1, 58, 50, 42, 34, 26, 18,
                    10, 2, 59, 51, 43, 35, 27,
                    19, 11, 3, 60, 52, 44, 36,
                    63, 55, 47, 39, 31, 23, 15,
                    7, 62, 54, 46, 38, 30, 22,
                    14, 6, 61, 53, 45, 37, 29,
                    21, 13, 5, 28, 20, 12, 4
            };
    private static int[] PC2 =
            {
                    14, 17, 11, 24, 1, 5,
                    3, 28, 15, 6, 21, 10,
                    23, 19, 12, 4, 26, 8,
                    16, 7, 27, 20, 13, 2,
                    41, 52, 31, 37, 47, 55,
                    30, 40, 51, 45, 33, 48,
                    44, 49, 39, 56, 34, 53,
                    46, 42, 50, 36, 29, 32
            };
    private static int[] IP =
            {
                    58, 50, 42, 34, 26, 18, 10, 2,
                    60, 52, 44, 36, 28, 20, 12, 4,
                    62, 54, 46, 38, 30, 22, 14, 6,
                    64, 56, 48, 40, 32, 24, 16, 8,
                    57, 49, 41, 33, 25, 17, 9, 1,
                    59, 51, 43, 35, 27, 19, 11, 3,
                    61, 53, 45, 37, 29, 21, 13, 5,
                    63, 55, 47, 39, 31, 23, 15, 7
            };
    private static int[] IP_r =
            {
                    40, 8, 48, 16, 56, 24, 64, 32,
                    39, 7, 47, 15, 55, 23, 63, 31,
                    38, 6, 46, 14, 54, 22, 62, 30,
                    37, 5, 45, 13, 53, 21, 61, 29,
                    36, 4, 44, 12, 52, 20, 60, 28,
                    35, 3, 43, 11, 51, 19, 59, 27,
                    34, 2, 42, 10, 50, 18, 58, 26,
                    33, 1, 41, 9, 49, 17, 57, 25
            };
    private static int[] P =
            {
                    16, 7, 20, 21,
                    29, 12, 28, 17,
                    1, 15, 23, 26,
                    5, 18, 31, 10,
                    2, 8, 24, 14,
                    32, 27, 3, 9,
                    19, 13, 30, 6,
                    22, 11, 4, 25
            };

    private static int[] E =
            {
                    32, 1, 2, 3, 4, 5,
                    4, 5, 6, 7, 8, 9,
                    8, 9, 10, 11, 12, 13,
                    12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21,
                    20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29,
                    28, 29, 30, 31, 32, 1
            };
    private static int[][] S1 = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    private static int[][] S2 = {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    private static int[][] S3 = {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };
    private static int[][] S4 = {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };
    private static int[][] S5 = {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
    private static int[][] S6 = {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
    private static int[][] S7 = {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    private static int[][] S8 = {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };
    private static int[] KEY_SHIFTS =
            {
                    1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
            };

    /**
     * Encryption function
     * @return encrypted binary string
     */
    public String encrypt(String key, String plaintext) {
        generate_keys(strToBin(key));
        // Initial permutation on binary plain text
        String ip = initial_permutation(strToBin(plaintext));

        String[] L = new String[17];
        String[] R = new String[17];
        // Divide the permuted block into two halves of 32 bits
        L[0] = ip.substring(0, 32);
        R[0] = ip.substring(32);

        for (int i = 0; i < 16; i++) {
            L[i + 1] = R[i];
            R[i + 1] = xor(L[i], f(R[i], keys[i]));
        }
        // Destroy the keys
        for (int i = 0; i < keys.length; i++) keys[i] = String.valueOf(0);
        // Final permutation
        return final_permutation(R[16] + L[16]);
    }

    /**
     * Decryption function
     * @param cypherText - binary string
     * @return decrypted plain text string
     */
    public String decrypt(String key, String cypherText) {
        generate_keys(strToBin(key));
        // Initial permutation on binary cypher text
        String ip = initial_permutation(cypherText);

        String[] L = new String[17];
        String[] R = new String[17];
        // Divide the permuted block into two halves of 32 bits */
        L[16] = ip.substring(0, 32);
        R[16] = ip.substring(32);

        for (int i = 16; i > 0; i--) {
            L[i - 1] = R[i];
            R[i - 1] = xor(L[i], f(R[i], keys[i - 1]));
        }

        // Destroy the keys
        for (int i = 0; i < keys.length; i++) keys[i] = String.valueOf(0);
        // Final permutation
        return binToStr(final_permutation(R[0] + L[0]));
    }

    /**
     * Creates 16 subkeys, each of which is 48-bits long.
     */
    public void generate_keys(String key) {
        // Get the 56-bit permutation from the original key using PC1_permutation function
        String pc1 = PC1_permutation(key);
        String[] LK = new String[17];
        String[] RK = new String[17];
        // Split the permuted key in half, each half has 28 bits
        LK[0] = pc1.substring(0, 28);
        RK[0] = pc1.substring(28);
        for (int i = 0; i < 16; i++) {
            // Perform left shifts according to KEY_SHIFTS array
            LK[i + 1] = shift_key(LK[i], i);
            RK[i + 1] = shift_key(RK[i], i);
            // Combine the two halves and build 48-bit sub keys using PC2_permutation function
            keys[i] = PC2_permutation(LK[i + 1] + RK[i + 1]);
        }
    }

    /**
     * Applies pc1 permutation on key according to PC1 array
     */
    public String PC1_permutation(String key) {
        String permutatedKey = "";
        for (int i : PC1) {
            permutatedKey += key.charAt(i - 1);
        }
        return permutatedKey;
    }

    /**
     * Applies pc2 permutation on key according to PC2 array
     */
    public String PC2_permutation(String key) {
        String permutatedKey = "";
        for (int i : PC2) {
            permutatedKey += key.charAt(i - 1);
        }
        return permutatedKey;
    }

    /**
     * Applies p permutation on massage according to P array
     */
    public String P_permutation(String input) {
        String permutatedInput = "";
        for (int i : P) {
            permutatedInput += input.charAt(i - 1);
        }
        return permutatedInput;
    }

    /**
     * Applies initial permutation on massage according to IP array
     */
    public String initial_permutation(String input) {
        String permutatedInput = "";
        for (int i : IP) {
            permutatedInput += input.charAt(i - 1);
        }
        return permutatedInput;
    }

    /**
     * Applies final permutation on massage according to IP_r array
     */
    public String final_permutation(String input) {
        String permutatedInput = "";
        for (int i : IP_r) {
            permutatedInput += input.charAt(i - 1);
        }
        return permutatedInput;
    }

    /**
     * E-Bit Expansion function according to E array
     */
    public String E(String input) {
        String result = "";
        for (int i : E) {
            result += input.charAt(i - 1);
        }
        return result;
    }

    /**
     * Function f steps:
     *  1. xor the output of E(previous left block) and the current key
     *  2. run the result from 1 thru S-BOX function
     *  3. combine all the results into a 32-bit output
     *  4. apply P_permutation function on the output
     *  5. return the final 32-bit output
     */
    public String f(String x1, String x2) {
        String permutated_x1 = E(x1);
        String x3 = xor(permutated_x1, x2);
        String result = "";
        String[] b = new String[8];
        b[0] = x3.substring(0, 6);
        b[1] = x3.substring(6, 12);
        b[2] = x3.substring(12, 18);
        b[3] = x3.substring(18, 24);
        b[4] = x3.substring(24, 30);
        b[5] = x3.substring(30, 36);
        b[6] = x3.substring(36, 42);
        b[7] = x3.substring(42);
        for (int i = 0; i < 8; i++) {
            String row_str = "";
            row_str += String.valueOf(b[i].charAt(0));
            row_str += String.valueOf(b[i].charAt(5));
            String column_str = "";
            column_str += String.valueOf(b[i].charAt(1));
            column_str += String.valueOf(b[i].charAt(2));
            column_str += String.valueOf(b[i].charAt(3));
            column_str += String.valueOf(b[i].charAt(4));
            int row = Integer.parseInt(row_str, 2);
            int column = Integer.parseInt(column_str, 2);
            result += S_BOX(row, column, i);
        }
        return P_permutation(result);
    }

    /**
     * S_BOX function takes a 6-bit block as input and yields a 4-bit block as output according to S1|S2|S3|...|S8 table
     */
    public String S_BOX(int row, int column, int i) {
        String output = "";
        switch (i) {
            case 0:
                output = Integer.toBinaryString(S1[row][column]);
                break;
            case 1:
                output = Integer.toBinaryString(S2[row][column]);
                break;
            case 2:
                output = Integer.toBinaryString(S3[row][column]);
                break;
            case 3:
                output = Integer.toBinaryString(S4[row][column]);
                break;
            case 4:
                output = Integer.toBinaryString(S5[row][column]);
                break;
            case 5:
                output = Integer.toBinaryString(S6[row][column]);
                break;
            case 6:
                output = Integer.toBinaryString(S7[row][column]);
                break;
            case 7:
                output = Integer.toBinaryString(S8[row][column]);
                break;
        }
        // Adds leading zero if its missing
        if (output.length() == 3) return "0" + output;
        if (output.length() == 2) return "00" + output;
        if (output.length() == 1) return "000" + output;
        if (output.length() == 0) return "0000" + output;
        else return output;
    }

    /**
     * Function to convert string to binary
     */
    public String strToBin(String value) {
        byte[] bytes = value.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    /**
     * Function to convert binary to string
     */
    public String binToStr(String value) {
        String result = "";
        for (int i = 0; i < value.length()/8; i++) {
            int a = Integer.parseInt(value.substring(8*i,(i+1)*8),2);
            result += (char)(a);
        }
        return result;
    }

    /**
     * Left shift function
     */
    public String shift_key(String key, int round) {
        int shifts = KEY_SHIFTS[round];
        String shifted_key = key.substring(shifts);
        shifted_key += key.substring(0, shifts);
        return shifted_key;
    }

    /**
     * XOR function
     */
    public String xor(String x1, String x2) {
        int length;
        String result = "";
        if (x1.length() >= x2.length()) length = x2.length();
        else length = x1.length();
        for (int i = 0; i < length; i++) {
            result += Integer.parseInt(x1.substring(i, i + 1)) ^ Integer.parseInt(x2.substring(i, i + 1));
        }
        return result;
    }
}
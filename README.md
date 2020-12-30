# DES
# How to test
Run `docker run maxpok/des` to test the algorithm.

## How DES Works in Detail
DES is a block cipher--meaning it operates on plaintext blocks of a given size (64-bits) and returns ciphertext blocks of the same size. Thus DES results in a permutation among the 2^64 (read this as: "2 to the 64th power") possible arrangements of 64 bits, each of which may be either 0 or 1. Each block of 64 bits is divided into two blocks of 32 bits each, a left half block L and a right half R. (This division is only used in certain operations.)
DES operates on the 64-bit blocks using key sizes of 56- bits. The keys are actually stored as being 64 bits long, but every 8th bit in the key is not used (i.e. bits numbered 8, 16, 24, 32, 40, 48, 56, and 64). However, we will nevertheless number the bits from 1 to 64, going left to right, in the following calculations. But, as you will see, the eight bits just mentioned get eliminated when we create subkeys.

# Step 1: Create 16 subkeys, each of which is 48-bits long.
The 64-bit key is permuted according to the following table, PC-1. Since the first entry in the table is "57", this means that the 57th bit of the original key K becomes the first bit of the permuted key K+. The 49th bit of the original key becomes the second bit of the permuted key. The 4th bit of the original key is the last bit of the permuted key. Note only 56 bits of the original key appear in the permuted key.

                            PC-1

              57   49    41   33    25    17    9
               1   58    50   42    34    26   18
              10    2    59   51    43    35   27
              19   11     3   60    52    44   36
              63   55    47   39    31    23   15
               7   62    54   46    38    30   22
              14    6    61   53    45    37   29
              21   13     5   28    20    12    4
              
Next, split this key into left and right halves, C0 and D0, where each half has 28 bits.
With C0 and D0 defined, we now create sixteen blocks Cn and Dn, 1<=n<=16. Each pair of blocks Cn and Dn is formed from the previous pair Cn-1 and Dn-1, respectively, for n = 1, 2, ..., 16, using the following schedule of "left shifts" of the previous block. To do a left shift, move each bit one place to the left, except for the first bit, which is cycled to the end of the block.

                     Iteration     Number of
                      Number      Left Shifts

                          1          1
                          2          1
                          3          2
                          4          2
                          5          2
                          6          2
                          7          2
                          8          2
                          9          1
                         10          2
                         11          2
                         12          2
                         13          2
                         14          2
                         15          2
                         16          1
                         
This means, for example, C3 and D3 are obtained from C2 and D2, respectively, by two left shifts, and C16 and D16 are obtained from C15 and D15, respectively, by one left shift. In all cases, by a single left shift is meant a rotation of the bits one place to the left, so that after one left shift the bits in the 28 positions are the bits that were previously in positions 2, 3,..., 28, 1.
We now form the keys Kn, for 1<=n<=16, by applying the following permutation table to each of the concatenated pairs CnDn. Each pair has 56 bits, but PC-2 only uses 48 of these.

                              PC-2

                 14    17   11    24     1    5
                  3    28   15     6    21   10
                 23    19   12     4    26    8
                 16     7   27    20    13    2
                 41    52   31    37    47   55
                 30    40   51    45    33   48
                 44    49   39    56    34   53
                 46    42   50    36    29   32

Therefore, the first bit of Kn is the 14th bit of CnDn, the second bit the 17th, and so on, ending with the 48th bit of Kn being the 32th bit of CnDn.

# Step 2: Encode each 64-bit block of data.
There is an initial permutation IP of the 64 bits of the message data M. This rearranges the bits according to the following table, where the entries in the table show the new arrangement of the bits from their initial order. The 58th bit of M becomes the first bit of IP. The 50th bit of M becomes the second bit of IP. The 7th bit of M is the last bit of IP.

                             IP

            58    50   42    34    26   18    10    2
            60    52   44    36    28   20    12    4
            62    54   46    38    30   22    14    6
            64    56   48    40    32   24    16    8
            57    49   41    33    25   17     9    1
            59    51   43    35    27   19    11    3
            61    53   45    37    29   21    13    5
            63    55   47    39    31   23    15    7

Next divide the permuted block IP into a left half L0 of 32 bits, and a right half R0 of 32 bits.
We now proceed through 16 iterations, for 1<=n<=16, using a function f which operates on two blocks--a data block of 32 bits and a key Kn of 48 bits--to produce a block of 32 bits. Let + denote XOR addition, (bit-by-bit addition modulo 2). Then for n going from 1 to 16 we calculate

Ln = Rn-1
Rn = Ln-1 + f(Rn-1,Kn)

This results in a final block, for n = 16, of L16R16. That is, in each iteration, we take the right 32 bits of the previous result and make them the left 32 bits of the current step. For the right 32 bits in the current step, we XOR the left 32 bits of the previous step with the calculation f .
It remains to explain how the function f works. To calculate f, we first expand each block Rn-1 from 32 bits to 48 bits. This is done by using a selection table that repeats some of the bits in Rn-1 . We'll call the use of this selection table the function E. Thus E(Rn-1) has a 32 bit input block, and a 48 bit output block.
Let E be such that the 48 bits of its output, written as 8 blocks of 6 bits each, are obtained by selecting the bits in its inputs in order according to the following table:

                    E BIT-SELECTION TABLE

                 32     1    2     3     4    5
                  4     5    6     7     8    9
                  8     9   10    11    12   13
                 12    13   14    15    16   17
                 16    17   18    19    20   21
                 20    21   22    23    24   25
                 24    25   26    27    28   29
                 28    29   30    31    32    1
                 
Thus the first three bits of E(Rn-1) are the bits in positions 32, 1 and 2 of Rn-1 while the last 2 bits of E(Rn-1) are the bits in positions 32 and 1.
(Note that each block of 4 original bits has been expanded to a block of 6 output bits.)
Next in the f calculation, we XOR the output E(Rn-1) with the key Kn:

Kn + E(Rn-1).

We have not yet finished calculating the function f . To this point we have expanded Rn-1 from 32 bits to 48 bits, using the selection table, and XORed the result with the key Kn . We now have 48 bits, or eight groups of six bits. We now do something strange with each group of six bits: we use them as addresses in tables called "S boxes". Each group of six bits will give us an address in a different S box. Located at that address will be a 4 bit number. This 4 bit number will replace the original 6 bits. The net result is that the eight groups of 6 bits are transformed into eight groups of 4 bits (the 4-bit outputs from the S boxes) for 32 bits total.

Write the previous result, which is 48 bits, in the form:

Kn + E(Rn-1) =B1B2B3B4B5B6B7B8,
where each Bi is a group of six bits. We now calculate

S1(B1)S2(B2)S3(B3)S4(B4)S5(B5)S6(B6)S7(B7)S8(B8)
where Si(Bi) referres to the output of the i-th S box.

To repeat, each of the functions S1, S2,..., S8, takes a 6-bit block as input and yields a 4-bit block as output. The table to determine S1 is shown and explained below:


                             S1

                        Column Number
            0  1   2  3   4  5   6  7   8  9  10 11  12 13  14 15
    Row N.
       0   14  4  13  1   2 15  11  8   3 10   6 12   5  9   0  7
       1    0 15   7  4  14  2  13  1  10  6  12 11   9  5   3  8
       2    4  1  14  8  13  6   2 11  15 12   9  7   3 10   5  0
       3   15 12   8  2   4  9   1  7   5 11   3 14  10  0   6 13
  
  
If S1 is the function defined in this table and B is a block of 6 bits, then S1(B) is determined as follows: The first and last bits of B represent in base 2 a number in the decimal range 0 to 3 (or binary 00 to 11). Let that number be i. The middle 4 bits of B represent in base 2 a number in the decimal range 0 to 15 (binary 0000 to 1111). Let that number be j. Look up in the table the number in the i-th row and j-th column. It is a number in the range 0 to 15 and is uniquely represented by a 4 bit block. That block is the output S1(B) of S1 for the input B. For example, for input block B = 011011 the first bit is "0" and the last bit "1" giving 01 as the row. This is row 1. The middle four bits are "1101". This is the binary equivalent of decimal 13, so the column is column number 13. In row 1, column 13 appears 5. This determines the output; 5 is binary 0101, so that the output is 0101. Hence S1(011011) = 0101.

The tables defining the functions S1,...,S8 are the following:

                             S1

     14  4  13  1   2 15  11  8   3 10   6 12   5  9   0  7
      0 15   7  4  14  2  13  1  10  6  12 11   9  5   3  8
      4  1  14  8  13  6   2 11  15 12   9  7   3 10   5  0
     15 12   8  2   4  9   1  7   5 11   3 14  10  0   6 13

                             S2

     15  1   8 14   6 11   3  4   9  7   2 13  12  0   5 10
      3 13   4  7  15  2   8 14  12  0   1 10   6  9  11  5
      0 14   7 11  10  4  13  1   5  8  12  6   9  3   2 15
     13  8  10  1   3 15   4  2  11  6   7 12   0  5  14  9

                             S3

     10  0   9 14   6  3  15  5   1 13  12  7  11  4   2  8
     13  7   0  9   3  4   6 10   2  8   5 14  12 11  15  1
     13  6   4  9   8 15   3  0  11  1   2 12   5 10  14  7
      1 10  13  0   6  9   8  7   4 15  14  3  11  5   2 12

                             S4

      7 13  14  3   0  6   9 10   1  2   8  5  11 12   4 15
     13  8  11  5   6 15   0  3   4  7   2 12   1 10  14  9
     10  6   9  0  12 11   7 13  15  1   3 14   5  2   8  4
      3 15   0  6  10  1  13  8   9  4   5 11  12  7   2 14

                             S5

      2 12   4  1   7 10  11  6   8  5   3 15  13  0  14  9
     14 11   2 12   4  7  13  1   5  0  15 10   3  9   8  6
      4  2   1 11  10 13   7  8  15  9  12  5   6  3   0 14
     11  8  12  7   1 14   2 13   6 15   0  9  10  4   5  3

                             S6

     12  1  10 15   9  2   6  8   0 13   3  4  14  7   5 11
     10 15   4  2   7 12   9  5   6  1  13 14   0 11   3  8
      9 14  15  5   2  8  12  3   7  0   4 10   1 13  11  6
      4  3   2 12   9  5  15 10  11 14   1  7   6  0   8 13

                             S7

      4 11   2 14  15  0   8 13   3 12   9  7   5 10   6  1
     13  0  11  7   4  9   1 10  14  3   5 12   2 15   8  6
      1  4  11 13  12  3   7 14  10 15   6  8   0  5   9  2
      6 11  13  8   1  4  10  7   9  5   0 15  14  2   3 12

                             S8

     13  2   8  4   6 15  11  1  10  9   3 14   5  0  12  7
      1 15  13  8  10  3   7  4  12  5   6 11   0 14   9  2
      7 11   4  1   9 12  14  2   0  6  10 13  15  3   5  8
      2  1  14  7   4 10   8 13  15 12   9  0   3  5   6 11

The final stage in the calculation of f is to do a permutation P of the S-box output to obtain the final value of f:

f = P(S1(B1)S2(B2)...S8(B8))
The permutation P is defined in the following table. P yields a 32-bit output from a 32-bit input by permuting the bits of the input block.

                                P

                         16   7  20  21
                         29  12  28  17
                          1  15  23  26
                          5  18  31  10
                          2   8  24  14
                         32  27   3   9
                         19  13  30   6
                         22  11   4  25

In the next round, we will have L2 = R1, which is the block we just calculated, and then we must calculate R2 =L1 + f(R1, K2), and so on for 16 rounds. At the end of the sixteenth round we have the blocks L16 and R16. We then reverse the order of the two blocks into the 64-bit block

R16L16
and apply a final permutation IP-1 as defined by the following table:

                             IP-1

            40     8   48    16    56   24    64   32
            39     7   47    15    55   23    63   31
            38     6   46    14    54   22    62   30
            37     5   45    13    53   21    61   29
            36     4   44    12    52   20    60   28
            35     3   43    11    51   19    59   27
            34     2   42    10    50   18    58   26
            33     1   41     9    49   17    57   25
            
That is, the output of the algorithm has bit 40 of the preoutput block as its first bit, bit 8 as its second bit, and so on, until bit 25 of the preoutput block is the last bit of the output.

### Decryption is simply the inverse of encryption, follwing the same steps as above, but reversing the order in which the subkeys are applied.


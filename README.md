# COMPX301-23A — Assignment Two

## LZ78 Compression

## Due: Saturday, April 29th, 2023 — 11:00pm

### Description: Implement LZ78 compression and decompression. This assignment is to be done in pairs (i.e. with a partner), and implemented in Java such that your code compiles and runs on the Linux machines in the R Block labs.

## Specification: Implement the LZ78 compression and decompression routines according to the following specifications:

An A solution will have two parts: an encoder, and a decoder. 
A complete A+ solution will have two additional parts, a bit-packer, and a bit-unpacker; 
all separate programs that read standard input and produce standard output.

Input to the encoder is a stream of bytes, but data is regarded as a stream of nibbles (i.e. four-bit symbols; hex digits). 
This should simplify encoding, and can be done with a separate byte-to-hex translator, or be embedded in your LZ encoder.

The encoder takes its hexadecimal input and uses LZ78 to convert it to integer pairs as output, one pair per line and space separated, 
where the first integer is the number of the longest matching phrase in the dictionary and the second number is the decimal value of the first mismatched hex symbol.

Given the output of your encoder as input, 
the output of your decoder should exactly match the input to the encoder (i.e. this is the complement operation to your encoder). 
The final output is a stream of bytes, so the hex-to-byte translation can be done within the decoder or afterward with a separate program.

Given the output of your encoder as input, 
the output of the bit-packer is a stream of bytes encoding the input in as few bits as are needed
(i.e. log p bits per phrase number when there are p patterns in the dictionary).

Given the output of your bit-packer as input, 
the output of your bit-unpacker should exactly match the input to the bit-packer 
(i.e. this is the complement operation to your bit-packer).

The encoder must utilise a multiway trie (not hashing) for the dictionary.
To assist in marking, the Java program classes must be called LZencode, LZdecode, LZpack and LZunpack, respectively.
Make sure you include instructions on usage in a README file (along with anything else you want to communicate to the marker) so that there's no difficulty with using all your code.
Approach: I suggest you get your byte-to-hex translation and detranslation going first. Then, get your encoder to work and build your decoder and then test them as a pair. Once this works, turn your attention to building the packer and unpacker. A mark of A is possible if just the encoder and decoder work, but a mark of A+ is only possible if all four programs are completed and comply with all specifications. You may elect to create shellscripts (i.e. batch files) to turn your programs into applications that can take a filename, if that proves useful to you ... but for marking it is useful to have the four separate programs using standard I/O.

Hand in: Submission is done electronically via moodle in the same way as the previous assignment. That is, place into an otherwise empty directory only your source code and a plain-text README file. Name the directory ID1_ID2, where ID1 and ID2 are the student ID numbers of the partnership. Create a single, compressed archive (see the unix manual on "zip" or "tar") and have one member of the partnership upload your solution via Moodle. The other partner should check that the upload was successful. Marks are awarded based upon conformance to the specification and quality of the code, including format and documentation (i.e. it should look nice and be understandable). Names and student ID numbers for both partners should be in the header documentation of all files.

Tony C. Smith, 29/03/2023

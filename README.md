# mpi-word-frequency

This app counts the word frequency for a given text file.

Can calculate the word frequency in parallel or in sequence.

Uses MPJ Express (http://mpjexpress.org/) MPI implementation.

1) To develop locally run `mvn clean install`
2) Compile the source code.
3) Launch the application with the following command.

`java −jar ./lib/starter.jar com.Main −np 3`
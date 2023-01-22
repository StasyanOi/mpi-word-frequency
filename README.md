# mpi-word-frequency

This app counts the word frequency for a given text file.

Can calculate the word frequency in parallel or in sequence.

Uses MPJ Express (http://mpjexpress.org/) MPI implementation.

Before development, run the **install_libs_to_maven.sh** script 
to add the required libs to the local Maven repository. 

1) To develop locally run `mvn clean install`
2) Compile the source code.
3) Launch the application with the following command `wordfreq.sh 3 ./text/input_text.txt`
   1) The first argument represents the thread count with which to do the counting task.
   2) The second argument represents the path to the text file where the word frequency needs to be found.
4) To see the results, you have to open up the **./results** folder
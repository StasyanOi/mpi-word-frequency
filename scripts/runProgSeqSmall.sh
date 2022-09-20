max=50

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_1_small_seq
    echo "$i" &>> results_1_small_seq
    java -cp ../out/production/mpi com.Other ./text/input_small.txt &>> results_1_small_seq
done
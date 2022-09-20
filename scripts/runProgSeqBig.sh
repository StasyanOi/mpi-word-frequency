max=50

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_1_big_seq
    echo "$i" &>> results_1_big_seq
    java -cp ../out/production/mpi com.Other ./text/input_big.txt &>> results_1_big_seq
done
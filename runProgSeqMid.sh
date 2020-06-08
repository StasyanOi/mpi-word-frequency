max=50

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_1_mid_seq
    echo "$i" &>> results_1_mid_seq
    java -cp /home/stanislav/IdeaProjects/mpi/out/production/mpi com.Other ./text/input_mid.txt &>> results_1_mid_seq
done
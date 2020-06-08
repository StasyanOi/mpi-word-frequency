max=50

export MPJ_HOME=/home/stanislav/mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_5_big
    echo "$i" &>> results_5_big
    java -jar /home/stanislav/mpj-v0_44/lib/starter.jar -cp /home/stanislav/IdeaProjects/mpi/out/production/mpi com.MainBig -np 5 &>> results_5_big
done
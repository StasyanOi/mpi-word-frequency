max=50

export MPJ_HOME=/home/stanislav/mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> resul3ts_4_mid
    echo "$i" &>> results_4_mid
    java -jar /home/stanislav/mpj-v0_44/lib/starter.jar -cp /home/stanislav/IdeaProjects/mpi/out/production/mpi com.MainMid -np 4 &>> results_4_mid
done
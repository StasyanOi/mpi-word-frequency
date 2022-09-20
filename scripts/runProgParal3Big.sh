max=50

export MPJ_HOME=../mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_3_big
    echo "$i" &>> results_3_big
    java -jar ../mpj-v0_44/lib/starter.jar -cp ../out/production/mpi com.MainBig -np 3 &>> results_3_big
done
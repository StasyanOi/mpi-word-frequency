max=50

export MPJ_HOME=../mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_4_mid
    echo "$i" &>> results_4_mid
    java -jar ../mpj-v0_44/lib/starter.jar -cp ../out/production/mpi com.MainMid -np 4 &>> results_4_mid
done
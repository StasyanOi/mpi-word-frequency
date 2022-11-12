max=50

export MPJ_HOME=../mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_4_small
    echo "$i" &>> results_4_small
    java -jar ../lib/starter.jar -cp ../target/classes com.MainSmall -np 4 &>> results_4_small
done
max=50

export MPJ_HOME=../mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_2_big
    echo "$i" &>> results_2_big
    java -jar ../lib/starter.jar -cp ../target/classes com.MainBig -np 2 &>> results_2_big
done
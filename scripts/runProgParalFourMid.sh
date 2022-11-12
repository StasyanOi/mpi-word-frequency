max=50

export MPJ_HOME=../mpj-v0_44

for (( i = 0; i < max; i++ )); do
    echo "----------" &>> results_4_mid
    echo "$i" &>> results_4_mid
    java -jar ../lib/starter.jar -cp ../target/classes com.MainMid -np 4 &>> results_4_mid
done
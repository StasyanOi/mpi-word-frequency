LIBS_TO_INSTALL=$(ls ./lib)

for library in $LIBS_TO_INSTALL ; do
  library=${library%".jar"}
  if [find $HOME/.m2/repository -type f -name $library]; then
    echo $library
    mvn install:install-file -Dfile=./lib/$library.jar -DgroupId=mpi.local -DartifactId=$library -Dversion=LOCAL -Dpackaging=jar
  fi
done
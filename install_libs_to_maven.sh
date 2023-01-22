LIBS_TO_INSTALL=$(ls ./lib)

for library in $LIBS_TO_INSTALL ; do
  library=${library%".jar"}
  echo $library
  mvn install:install-file -Dfile=./lib/$library.jar -DgroupId=mpi.local -DartifactId=$library -Dversion=LOCAL -Dpackaging=jar
done
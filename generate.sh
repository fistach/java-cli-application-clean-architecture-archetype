rm -rf test

mkdir test
cd test

mvn archetype:generate \
  -DarchetypeGroupId=pl.fistach \
  -DarchetypeArtifactId=java-cli-clean-architecture-archetype \
  -DgroupId=pl.fistach \
  -DartifactId=calculator \
  -Dversion=1.0-SNAPSHOT \
  -Dpackage=pl.main \
  -DinteractiveMode=false

cd ..

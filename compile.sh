rm -r bin
rm -r filelist.txt
mkdir bin
cp ./src/pool_test.jocl ./bin/pool_test.jocl
cp ./src/pool_RevIFMain.jocl ./bin/pool_RevIFMain.jocl
find ./src -name *.java -print > filelist.txt
javac -d ./bin -cp ./lib/commons-dbcp-1.4.jar:./lib/jsch-0.1.55.jar:./lib/ojdbc8.jar:./lib/commons-lang3-3.1.jar:./lib/commons-pool-1.4.jar:./lib/commons-io-2.11.0.jar @filelist.txt

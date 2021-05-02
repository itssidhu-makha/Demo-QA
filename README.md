# Demo-QA
1. This framework can be run from Runner.Java file as a main class
2. It will by default run 2 threads in parallel. We can change number of threads to 1 from Runner.Java global variables
3. It can be run from folder "RunTests" by clicking on RunTests.bat file
4. Extent report are integrated along with surefire reports
5. Extent Reports can be accessed from "..\DemoQA\src\main\resources\reports"
6. For every test case a new report will be created
7. Data sets for test cases are available under "..DemoQA\src\main\resources\datas"
8. It has inbuilt Docker support to bring up the containers using yaml file
9. We can use VNC Viewer or run via Zalenium containers to see the actual execution happening in real time
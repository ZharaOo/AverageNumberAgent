 # Average Number Between Agents

### Авторы: Иван Бабкин, Артем Епрев, Дарья Ткачева

## Оценка алгоритма

Память: O(N);  
Кол-во обращений в центр: 1;  
Кол-во обменов сообщениями между агентами: 2D*M +2M;  
Кол-во тактов: 3D.  

D -- диаметр графа, N -- кол-во агентов, M -- кол-во связей

## Запуск программы

##### Создание агента в аргументах при запуске:

AgentName:AgentClass(diameter,neighbour1,neighbour2,...)

##### Main class:

jade.Boot

##### Пример аргументов программы при запуске:

-gui
-local-port 1111
Agent1:AverageNumberAgent(6,Agent2,Agent3);Agent2:AverageNumberAgent(6,Agent1,Agent4);Agent3:AverageNumberAgent(6,Agent1,Agent4);Agent4:AverageNumberAgent(6,Agent2,Agent3,Agent5);Agent5:AverageNumberAgent(6,Agent4,Agent6);Agent6:AverageNumberAgent(6,Agent5,Agent7,Agent9);Agent7:AverageNumberAgent(6,Agent6,Agent8);Agent8:AverageNumberAgent(6,Agent7,Agent9);Agent9:AverageNumberAgent(6,Agent6,Agent8);

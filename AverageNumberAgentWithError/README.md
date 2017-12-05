 # Average Number Between Agents With Error
 
 Генерируемые ошибки:
 1. Обрыв связи
 2. Помехи при передаче данных
 3. Задержка отправки данных

Main class: jade.Boot

Аргументы программы при запуске:

-gui
-local-port
1111
Agent1:AverageNumberAgent(Agent2,Agent3);Agent2:AverageNumberAgent(Agent1,Agent4);Agent3:AverageNumberAgent(Agent1,Agent4);Agent4:AverageNumberAgent(Agent2,Agent3,Agent5);Agent5:AverageNumberAgent(Agent4,Agent6);Agent6:AverageNumberAgent(Agent5,Agent7,Agent9);Agent7:AverageNumberAgent(Agent6,Agent8);Agent8:AverageNumberAgent(Agent7,Agent9);Agent9:AverageNumberAgent(Agent6,Agent8);

AgentName:AgentClass(neighbour1,neighbour2,...)

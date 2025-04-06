# FlightsFilter (Фильтр полетов)

## Содержание
1. [О проекте](#о-проекте)
2. [Особенности проекта](#особенности-проекта)
3. [Пример работы модуля](#пример-работы-модуля)
4. [Сборка](#сборка)

## О проекте

В этом небольшом проекте был реализован модуль, который занимается фильтрацией полетов. На вход из потока поступают данные в виде полетов (полет - это класс, который состоит из списка сегментов, которые в свою очередь включают в себя время отправления и время прибытия). Далее в менеджере фильтров добавляются и применяются необходимые нам фильтры, которые отсеивают не подходящие под условия полеты. В итоге мы получаем уже новый список полетов, который можно вывести в консоль. <br>

## Особенности проекта

Модуль фильтрации способен обрабатывать большие наборы полетов и динамически подключать/отключать необходимые для задачи фильтры. В качестве примера были созданы следующие 3 фильтра, совместная работа которых представлена на скриншоте ниже (запуск происходит через `Main-класс`):
1) Отбор полетов, отправление у которых происходит в будущем (полеты, вылет у которых уже состоялся, отсеиваются);
2) Исключение полетов, у которых время отправления позже времени прибытия (то есть эффект назад в прошлое, например, при неправильном вводе данных);
3) Время между всеми пересадками не превышает заданное количество часов (задается отдельным параметром).<br>

	<div align=center>
    
	![Вывод отфильтрованных полетов в консоль](images/consoleoutput.png)
	*Вывод отфильтрованных полетов в консоль*
	</div>

Представлены следующие паттерны проектирования:

1) Стратегия - имеется общий функциональный интерфейс `FlightFilter`, содержащий единственный метод filter, реализация которого представлена непосредственно в классах-фильтрах, имплементирующих данный интерфейс;
2) Фабричный метод - классы-фабрики (`FilterFactory` и `FlightBuilder`), которые скрывают от внешнего взора свои внутренние процессы по созданию объектов;
3) Синглтон - объект класса `FilterHolder` (содержит в себе все применяемые фильтры) возможен только в единственном экземпляре в модуле.

Использовался JUnit для покрытия модуль тестами. Код покрыт на 80% и выше:
   	<div align=center>
    ![Покрытие кода тестами](images/testcoverage.png)
  	*Покрытие кода тестами*
  	</div>


## Пример работы модуля

В качестве примера в файле с фильтрами `src/main/resources/flights.txt` пропишем даты тестовых полетов (один полет на одной строчке, сегменты разделены пробелами в формате "yyyy-MM-dd'T'HH:mm"),
```
2025-02-25T15:28 2025-02-25T20:37
2025-04-08T11:11 2025-04-08T12:34 2025-04-08T14:08 2025-04-08T16:23
2025-04-05T09:46 2025-04-05T14:05
2025-04-09T12:42 2025-04-09T14:58
2025-04-06T15:03 2025-04-06T16:30 2025-04-06T19:48 2025-04-06T20:06 2025-04-06T23:34 2025-04-07T00:45
```
В файле `src/main/resources/filters.txt` укажем названия 2 фильтров (на каждой новой строчке) - пропуск полетов, которые уже были отправлены и имеющие пересадку в совокупности более 3 часов. Для фильтра с параметром пропишем значение через пробел :
```
TotalHoursTransfersFilter 3
DepartureBeforeNowFilter
```

В main укажем название наших файлов и вызовем метод для печати в консоль отфильтрованных полетов:
```
public class Main {
    public static void main(String[] args) {
        String filtersFile = "filters.txt";
        String flightsFile = "flights.txt";
        FlightFilterService.printFilteredFlights(filtersFile, flightsFile);
    }
}
```
В консоли выведется информация о процессе фильтрации:
	<div align=center>
	![Тестовая фильтрация](images/exampleconsoleoutput.png)
	*Тестовая фильтрация*
	</div>

* Помимо вывода в консоль список готовых отфильтрованных полетов можно получить другим методом сервиса: `public static List<Flight> getFilteredFlights(String filtersFile, String flightsFile)`.
* В `FiltersHolder` есть возможность добавления и удаления имеющихся в модуле фильтров `public void addFilter(FlightFilter filter), public void removeFilter(FlightFilter filter)`.
* Для того, чтобы создать новый фильтр, необходимо добавить новый класс в `src/main/java/com/gridnine/testing/service/filter/impl`, а также добавить его в поле фабрики `FilterFactory`.

## Сборка

Проект собирается с помощью Gradle (Kotlin DSL), JDK 18. Ниже представлены команды, которые можно прописать в консоли в корне проекта:

| Команда                                          | Описание                                                                |
|--------------------------------------------------|-------------------------------------------------------------------------|
| `./gradlew run`                                  | Запускает приложение напрямую без сборки JAR-файла                      |
| `./gradlew jar`                                  | Создает исполняемый JAR `build/libs/flight-filter-1.0-all.jar`          |
| `./gradlew test`                                 | Запускает unit-тесты и генерирует отчет в HTML `build/reports/tests/test/index.html` |
| `./gradlew clean`                                | Очищает каталог сборки (`build/`), удаляя все сгенерированные файлы     |
| `java -jar build/libs/flight-filter-1.0-all.jar` | Запуск собранного JAR (Linux/macOS)                                     |
| `javaw -jar build/libs/flight-filter-1.0-all.jar`| Запуск JAR без консоли (Windows)                                        |



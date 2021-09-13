<h1>Тестовое задание</h1>

<h3>Задача:</h3> используя Java Reflection API и классы из пакета java.lang.reflect реализовать простейшую версию Dependency Injection-контейнера.
Результатом работы должен стать Maven (или Gradle) проект, который может собираться в одну или несколько JAR-библиотек.
Сторонние библиотеки использовать запрещено. Весь код должен быть написан вами. За исключением библиотек для тестирования.
Для проверки работы библиотеки должны быть написаны Unit-тесты.
Задание разбито на несколько пунктов.
В сопроводительном письме, укажите:
	- Какие пункты вы выполнили полностью
	- А какие - частично
Задание целиком выполнять не обязательно.

<h3>Требования к реализации:</h3>
1.  Запрещается использовать сторонние библиотеки (кроме юнит тестирования)
2.  Реализацию необходимо осуществить в классе InjectorImpl на основе интерфейса Injector:<br><br>
 <i>public interface Injector {<br>
    <T> Provider<T> getProvider(Class<T> type); //получение инстанса класса со всеми иньекциями по классу интерфейса <br>
    <T> void bind(Class<T> intf, Class<? extends T> impl); //регистрация байндинга по классу интерфейса и его реализации<br>
    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl); //регистрация синглтон класса <br>
}</i>

<h3>Где</h3><br>
  <i>public interface Provider<T><br>
  {    <br>
       T getInstance();   <br>
  }</i>

3. Для осуществления байндинга в конструктор класса имплементации добавляется аннотация @Inject

<h3>Например:</h3><br>
   <i>pubic class  EventServiceImpl implements EventService {<br>
        private EventDao eventDao;	<br>
        @Inject<br>
        public EventServiceImpl(EventDao eventDao) {<br>
            this.eventDao = eventDao;<br>
         }<br>
   }<br></i>
4. Если в классе присутствует несколько конструкторов с аннотацией @Inject, выбрасывается TooManyConstructorsException.<br>
5. При отсутствии конструкторов с аннотацией Inject используется конструктор по умолчанию. При
отсутствии такового выбрасывается ConstructorNotFoundException.<br>
6. Если контейнер использует конструктор с аннотацией Inject и для какого-либо аргумента контейнер
не может найти binding, выбрасывается BindingNotFoundException.<br>
7. Если мы запрашиваем Provider для какого-либо класса и нет cоответствующего binding, возвращается null.<br>
8. Реализуйте возможность использования Singleton и Prototype бинов.<br>
9. Реализация singleton binding'ов должна быть ленивой (создание объекта при первом обращении).<br>
10. Реализация получения провайдеров должна быть потокобезопасной<br>
11. Поддержка field и method injection не требуется - Inject только через конструкторы.<br>
12. Все аргументы конструкторов гарантировано являются reference type'ами. То есть не предполагается передача в конструкторы аргументов простых типов.<br>
13. Все конструкторы являются public<br><br>
<h3>Задача:</h3><br>
- Создать интерфейсы Injector, Provider, необходимые классы исключений и аннотацию @Inject.<br>
- Реализовать имплементацию InjectorImpl, которая:<br>
	1. Сканирует конструкторы класса<br>
	2. Сканирует аннотации @Inject конструкторов при байндинге <br>
	3. Создающую инстансы классов и инжекцию параметров конструктора при обращении<br>  
                  (Singleton или Prototype).<br>
- написать юнит тесты, покрывающие тест кейсы<br>

<h3>Пример теста:</h3><br>
  <i>  @Test<br>
    void testExistingBinding()     {<br>
        Injector injector = new InjectorImpl(); //создаем имплементацию инжектора<br>
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class); //добавляем в инжектор реализацию интерфейса<br>
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class); //получаем инстанс класса из инжектора<br>
        assertNotNull(daoProvider); <br>
        assertNotNull(daoProvider.getInstance());<br>
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());<br>
    }</i>

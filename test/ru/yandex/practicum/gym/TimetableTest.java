package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleSession);


        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size(), "В понедельник должно быть 1 занятие");
        Assertions.assertEquals(singleSession, monday.get(0), "Должно вернуться именно то занятие, которое добавили");

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty(), "Во вторник занятий быть не должно");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupChild = new Group("Дети", Age.CHILD, 60);
        Group groupAdult = new Group("Взрослые", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession session2 = new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);


        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursday.size());
        Assertions.assertEquals(13, thursday.get(0).getTimeOfDay().getHours(), "Первым должно идти занятие на 13:00");
        Assertions.assertEquals(20, thursday.get(1).getTimeOfDay().getHours(), "Вторым должно идти занятие на 20:00");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TimeOfDay time13 = new TimeOfDay(13, 0);

        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.MONDAY, time13));


        List<TrainingSession> sessions13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time13);
        Assertions.assertEquals(1, sessions13.size());


        List<TrainingSession> sessions14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(sessions14.isEmpty());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Йога", Age.ADULT, 60);


        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> counts = timetable.getCountByCoaches();

        Assertions.assertEquals(2, counts.size());
        Assertions.assertEquals("Васильев", counts.get(0).getCoach().getSurname());
        Assertions.assertEquals(2, counts.get(0).getCount());
    }

    @Test
    void testMultipleSessionsAtTheSameTime() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Group group1 = new Group("Бокс", Age.ADULT, 60);
        Group group2 = new Group("Йога", Age.ADULT, 60);
        TimeOfDay morning = new TimeOfDay(10, 0);


        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.MONDAY, morning));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach2, DayOfWeek.MONDAY, morning));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, morning);

        Assertions.assertEquals(2, sessions.size(), "Должно быть 2 занятия в одно время");
        Assertions.assertTrue(sessions.stream().anyMatch(s -> s.getCoach().getSurname().equals("Васильев")));
        Assertions.assertTrue(sessions.stream().anyMatch(s -> s.getCoach().getSurname().equals("Петров")));
    }

    @Test
    void testGetSessionsForEmptyDayAndTime() {
        Timetable timetable = new Timetable();

        timetable.addNewTrainingSession(new TrainingSession(
                new Group("Тест", Age.ADULT, 60),
                new Coach("А", "Б", "В"),
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));


        List<TrainingSession> emptyDay = timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY);
        Assertions.assertNotNull(emptyDay, "Метод не должен возвращать null");
        Assertions.assertTrue(emptyDay.isEmpty(), "Список за пустой день должен быть пустым");


        List<TrainingSession> emptyTime = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        Assertions.assertTrue(emptyTime.isEmpty(), "Список за пустое время должен быть пустым");
    }

}

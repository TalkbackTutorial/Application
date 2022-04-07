package com.github.talkbacktutorial.lessons

/*
    LessonContainer contains all the lessons. This means any Activity can access any and all
    activity at any time. This means Lessons don't need to be serialised and passed around,
    which creates a lot of boilerplate code and restrictions.
 */
object LessonContainer {

    private val lessons = ArrayList<Lesson>(listOf(
        Lesson0(),
        Lesson1(),
        Lesson2()
    ))

    fun getAllLessons(): ArrayList<Lesson> {
        return ArrayList(lessons)
    }

    fun getLesson(id: String): Lesson {
        return lessons.first { lesson ->
            lesson.id.toString() == id
        }
    }

}
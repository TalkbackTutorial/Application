package com.github.talkbacktutorial.lessons

/**
 * LessonContainer contains all the lessons. This means any Activity can access any and all
 * activities at any time. This means Lessons don't need to be serialised and passed around, which
 * creates a lot of boilerplate code and restrictions.
 * @author Andre Pham
 */
object LessonContainer {

    private val lessons = ArrayList<Lesson>(
        listOf(
            Lesson1(),
            Lesson2(),
            Lesson3(),
            Lesson4(),
            Lesson5(),
            Lesson6(),
        //  Lesson7()
        )
    )

    /**
     * Retrieves all lessons in a new ArrayList.
     * @author Andre Pham
     */
    fun getAllLessons(): ArrayList<Lesson> {
        return ArrayList(lessons)
    }

    /**
     * Retrieves a lesson by its sequence numeral.
     * @author Andre Pham
     */
    fun getLesson(sequenceNumeral: Int): Lesson {
        return this.lessons.first { it.sequenceNumeral == sequenceNumeral }
    }

    /**
     * Retrieves a single lesson by ID.
     * @author Andre Pham
     */
    fun getLesson(id: String): Lesson {
        return lessons.first { lesson ->
            lesson.id.toString() == id
        }
    }
}

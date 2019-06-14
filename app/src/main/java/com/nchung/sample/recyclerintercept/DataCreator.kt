package com.nchung.sample.recyclerintercept

object DataCreator {

    operator fun invoke() = listOf(
        getParent(1),
        getParent(2),
        getParent(3),
        getParent(4),
        getParent(5),
        getParent(6)
    )

    private fun getParent(id: Int): Parent {
        return Parent(
            "Social $id",
            listOf(
                Child("Twitter $id", R.drawable.ic_twitter),
                Child("Facebook $id", R.drawable.ic_facebook),
                Child("Instagram $id", R.drawable.ic_instagram),
                Child("Youtube $id", R.drawable.ic_youtube)
            )
        )
    }
}

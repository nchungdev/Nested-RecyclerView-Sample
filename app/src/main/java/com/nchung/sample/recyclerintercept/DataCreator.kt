package com.nchung.sample.recyclerintercept

object DataCreator {

    operator fun invoke() = listOf(
        Parent(
            "Social",
            listOf(
                Child("Twitter", R.drawable.ic_twitter),
                Child("Facebook", R.drawable.ic_facebook),
                Child("Instagram", R.drawable.ic_instagram),
                Child("Youtube", R.drawable.ic_youtube)
            )
        )
    )
}

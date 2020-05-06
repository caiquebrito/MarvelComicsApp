package com.marvelcomics.brito.infrastructure.utils

class MarvelThumbnailAspectRatio {
    //resolution at https://developer.marvel.com/documentation/images
    object Portrait {
        const val SMALL = "portrait_small"
        const val MEDIUM = "portrait_medium"
        const val XLARGE = "portrait_xlarge"
        const val FANTASTIC = "portrait_fantastic"
        const val UNCANNY = "portrait_uncanny"
        const val INCREDIBLE = "portrait_incredible"
    }

    object Standard {
        const val SMALL = "standard_small"
        const val MEDIUM = "standard_medium"
        const val LARGE = "standard_large"
        const val XLARGE = "standard_xlarge"
        const val FANTASTIC = "standard_fantastic"
        const val AMAZING = "standard_amazing"
    }

    object Landscape {
        const val SMALL = "landscape_small"
        const val MEDIUM = "landscape_medium"
        const val LARGE = "landscape_large"
        const val XLARGE = "landscape_xlarge"
        const val AMAZING = "landscape_amazing"
        const val INCREDIBLE = "landscape_incredible"
    }

    object Full {
        const val FULLSIZE = ""
    }
}
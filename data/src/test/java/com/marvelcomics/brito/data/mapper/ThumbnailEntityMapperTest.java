package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.entity.ThumbnailEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(MockitoJUnitRunner.class)
public class ThumbnailEntityMapperTest {

    @Mock
    private ThumbnailResponse mockThumbnailResponse;
    @Spy
    private ThumbnailEntityMapper spyThumbnailEntityMapper;

    @Test
    public void thumbnailEntityMapper_transform_success() {
        Object object = spyThumbnailEntityMapper.transform(mockThumbnailResponse);
        assertThat("Object should return " + ThumbnailEntity.class.getCanonicalName(),
                object, instanceOf(ThumbnailEntity.class));
    }
}

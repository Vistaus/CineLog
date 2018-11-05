package com.ulicae.cinelog.exportdb;

import com.ulicae.cinelog.dao.LocalKino;
import com.ulicae.cinelog.dao.TmdbKino;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CsvExportWriterTest {

    @Mock
    private CSVPrinterWrapper csvPrinterWrapper;

    @Mock
    private LocalKino aKino;

    @Mock
    private TmdbKino tmdbKino;

    @Test
    public void write() throws Exception {
        doReturn(tmdbKino).when(aKino).getKino();

        Date aDate = new Date();
        //noinspection ResultOfMethodCallIgnored
        doReturn(aDate).when(aKino).getReview_date();

        new CsvExportWriter(csvPrinterWrapper).write(aKino);

        verify(csvPrinterWrapper).printRecord(
                tmdbKino.getMovie_id(),
                aKino.getTitle(),
                tmdbKino.getOverview(),
                tmdbKino.getYear(),
                tmdbKino.getPoster_path(),
                aKino.getRating(),
                tmdbKino.getRelease_date(),
                aKino.getReview(),
                new SimpleDateFormat().format(aKino.getReview_date())
        );
    }

    @Test
    public void writeNullReviewDate() throws Exception {
        doReturn(tmdbKino).when(aKino).getKino();
        //noinspection ResultOfMethodCallIgnored
        doReturn(null).when(aKino).getReview_date();

        new CsvExportWriter(csvPrinterWrapper).write(aKino);

        verify(csvPrinterWrapper).printRecord(
                tmdbKino.getMovie_id(),
                aKino.getTitle(),
                tmdbKino.getOverview(),
                tmdbKino.getYear(),
                tmdbKino.getPoster_path(),
                aKino.getRating(),
                tmdbKino.getRelease_date(),
                aKino.getReview(),
                null
        );
    }

    @Test
    public void writeNullTmdbKino() throws Exception {
        doReturn(null).when(aKino).getKino();

        new CsvExportWriter(csvPrinterWrapper).write(aKino);

        verify(csvPrinterWrapper).printRecord(
                null,
                aKino.getTitle(),
                null,
                null,
                null,
                aKino.getRating(),
                null,
                aKino.getReview(),
                aKino.getReview_date()
        );
    }

    @Test
    public void endWriting() throws Exception {
        new CsvExportWriter(csvPrinterWrapper).endWriting();

        verify(csvPrinterWrapper).flush();
        verify(csvPrinterWrapper).close();
    }
}
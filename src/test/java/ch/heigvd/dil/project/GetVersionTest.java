package ch.heigvd.dil.project;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetVersionTest {
    @Test
    public void shouldGetAVersionArray() {
        ManifestVersionProvider provider = new ManifestVersionProvider();
        assertTrue(provider.getVersion().length > 0);
    }

    @Test
    public void shouldGetAVersionString() {
        ManifestVersionProvider provider = new ManifestVersionProvider();
        assertNotNull(provider.getVersion()[0]);
    }

    @Test
    public void shouldGetTheSameVersion() {
        ManifestVersionProvider provider = new ManifestVersionProvider();
        String[] version = provider.getVersion();
        if (!ManifestVersionProvider.isPackageVersionAvailable()) {
            assertEquals(version[0], ManifestVersionProvider.FALLBACK_VERSION);
        }
    }
}
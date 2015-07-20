package integration;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import com.beijunyi.parallelgit.AbstractParallelGitTest;
import com.beijunyi.parallelgit.filesystem.GitFileSystem;
import com.beijunyi.parallelgit.filesystem.utils.GitParams;
import com.beijunyi.parallelgit.filesystem.utils.GitUriBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileSystemsCreateTest extends AbstractParallelGitTest {

  @Before
  public void setupRepositoryDirectory() throws IOException {
    initRepositoryDir(false);
  }

  @Test
  public void createRepository() throws IOException {
    URI uri = GitUriBuilder.prepare()
                .repository(repoDir)
                .build();
    FileSystem fs = FileSystems.newFileSystem(uri, GitParams.emptyMap().setCreate(true));
    Assert.assertTrue(fs instanceof GitFileSystem);
  }

  @Test(expected = IllegalStateException.class)
  public void createRepository_whenOneAlreadyExists() throws IOException {
    initFileRepository(true);
    URI uri = GitUriBuilder.prepare()
                .repository(repoDir)
                .build();
    FileSystems.newFileSystem(uri, GitParams.emptyMap().setCreate(true));
  }

  @Test
  public void createBareRepository() throws IOException {
    URI uri = GitUriBuilder.prepare()
                .repository(repoDir)
                .build();
    FileSystem fs = FileSystems.newFileSystem(uri, GitParams.emptyMap().setCreate(true).setBare(true));
    Assert.assertEquals(repoDir, ((GitFileSystem) fs).getRepository().getDirectory());
  }

  @Test
  public void createNonBareRepository() throws IOException {
    URI uri = GitUriBuilder.prepare()
                .repository(repoDir)
                .build();
    FileSystem fs = FileSystems.newFileSystem(uri, GitParams.emptyMap().setCreate(true).setBare(false));
    Assert.assertEquals(repoDir, ((GitFileSystem) fs).getRepository().getWorkTree());
  }

}

package com.beijunyi.parallelgit.utils;

import java.io.IOException;

import com.beijunyi.parallelgit.AbstractParallelGitTest;
import com.beijunyi.parallelgit.utils.exception.RefUpdateRejectedException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BranchUtilsSetBranchHeadTest extends AbstractParallelGitTest {

  private final String branch = "test_branch";
  private RevCommit branchHead;

  @Before
  public void setUpBranch() throws IOException {
    AnyObjectId masterHead = initMemoryRepository(false);
    writeSomeFileToCache();
    branchHead = CommitUtils.getCommit(commitToBranch(branch, masterHead), repo);
  }

  @Test
  public void commitBranchHead_branchHeadShouldBecomeTheNewCommit() throws IOException {
    writeSomeFileToCache();
    AnyObjectId childCommit = commit(branchHead);
    BranchUtils.commitBranchHead(branch, childCommit, repo);
    Assert.assertEquals(childCommit, BranchUtils.getBranchHeadCommit(branch, repo));
  }

  @Test(expected = RefUpdateRejectedException.class)
  public void commitBranchHeadWhenInputCommitIsNotChildCommit_shouldThrowRefUpdateRejectedException() throws IOException {
    writeSomeFileToCache();
    AnyObjectId nonChildCommit = commit(null);
    BranchUtils.commitBranchHead(branch, nonChildCommit, repo);
  }

  @Test
  public void amendBranchHead_branchHeadShouldBecomeTheAmendedCommit() throws IOException {
    writeSomeFileToCache();
    AnyObjectId amendedCommit = commit(branchHead.getParent(0));
    BranchUtils.amendBranchHead(branch, amendedCommit, repo);
    Assert.assertEquals(amendedCommit, BranchUtils.getBranchHeadCommit(branch, repo));
  }

  @Test
  public void cherryPickBranchHead_branchHeadShouldBecomeTheCherryPickedCommit() throws IOException {
    writeSomeFileToCache();
    AnyObjectId cherryPickedCommit = commit(branchHead);
    BranchUtils.cherryPickBranchHead(branch, cherryPickedCommit, repo);
    Assert.assertEquals(cherryPickedCommit, BranchUtils.getBranchHeadCommit(branch, repo));
  }

  @Test(expected = RefUpdateRejectedException.class)
  public void cherryPickBranchHeadWhenInputCommitIsNotChildCommit_branchHeadShouldBecomeTheCherryPickedCommit() throws IOException {
    writeSomeFileToCache();
    AnyObjectId cherryPickedCommit = commit(null);
    BranchUtils.cherryPickBranchHead(branch, cherryPickedCommit, repo);
  }



}

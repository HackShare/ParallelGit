package com.beijunyi.parallelgit.util.exception;

import javax.annotation.Nonnull;

public class RefUpdateRejectedException extends RuntimeException {

  public RefUpdateRejectedException(@Nonnull String message) {
    super(message);
  }
}

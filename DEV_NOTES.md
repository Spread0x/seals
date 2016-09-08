<!--

   Copyright 2016 Daniel Urban

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

--->

# Notes for development

Unorganized notes which may be useful during development.

## Signing Git commits and tags

There are several tutorials available on signing
in Git (and using it on GitHub), for example
[this one](https://git-scm.com/book/en/v2/Git-Tools-Signing-Your-Work)
and [this one](https://help.github.com/categories/gpg/).

Here is a quick outline of the steps (if you're already
familiar with [GPG](https://www.gnupg.org) and Git,
this might be enough):

- the key you want to use must be in your GPG keyring
- note your 64-bit key ID with `gpg2 --list-keys --keyid-format long`
- configure Git to use the desired key for signing:
  `git config --global user.signingkey <your key ID>`
- you can tell Git to use GPG v2 with `git config --global gpg.program gpg2`
- make sure that signing commits is the default:
  `git config --global commit.gpgsign true` (you can omit the `--global`
  to only sign by default the commits of the current repository)
- make sure that signing annotated tags is the default:
  `git config --global tag.forcesignannotated true`

## GnuPG configuration

Useful config options for GPG v2 (put them into `~/.gnupg/gpg.conf`):

- `keyid-format long`: display the long (64-bit) key ID by default
- `with-fingerprint`: display the full key fingerprint by default
- `no-comments`: don't put comments into the `.asc` output
- `no-emit-version`: don't write the GPG version number into the `.asc` output
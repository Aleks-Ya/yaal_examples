# Use "snapshot" versions with `bump-my-version`

Show the next versions: `bump-my-version show-bump`
From SNAPSHOT to RELEASE: (`0.1.1-SNAPSHOT` -> `0.1.1`): `bump-my-version bump release`
From RELEASE to SNAPSHOT (`0.1.1` -> `0.2.0-SNAPSHOT`): `bump-my-version bump minor`
Increment SNAPSHOT major version (`0.1.1-SNAPSHOT` -> `1.0.0-SNAPSHOT`): `bump-my-version bump major`
Increment SNAPSHOT minor version (`0.1.1-SNAPSHOT` -> `0.2.0-SNAPSHOT`): `bump-my-version bump minor`
Increment SNAPSHOT patch version (`0.1.1-SNAPSHOT` -> `0.1.2-SNAPSHOT`): `bump-my-version bump patch`

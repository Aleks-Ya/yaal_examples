#!/usr/bin/env bats

@test "Check specified option" {
  getopts "x:" opt "-x abc"
  [ "$opt" = "x" ]
}

@test "Parse with while-case 1" {
  echo "OPTIND starts at $OPTIND"
  while getopts ":pq:" optname
    do
      case "$optname" in
        "p")
          echo "Option $optname is specified"
          ;;
        "q")
          echo "Option $optname has value $OPTARG"
          ;;
        "?")
          echo "Unknown option $OPTARG"
          ;;
        ":")
          echo "No argument value for option $OPTARG"
          ;;
        *)
        # Should not occur
          echo "Unknown error while processing options"
          ;;
      esac
      echo "OPTIND is now $OPTIND"
    done
}

@test "Parse with while-case 2" {
  while getopts ":a" opt; do
    case $opt in
      a)
        echo "-a was triggered!" >&2
        ;;
      \?)
        echo "Invalid option: -$OPTARG" >&2
        ;;
    esac
  done
}

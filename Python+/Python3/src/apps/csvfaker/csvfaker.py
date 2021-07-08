# -*- coding: utf-8 -*-
"""
Generate CSV files with fake data. Based on the Faker Python package.
Origin: https://github.com/pereorga/csvfaker
Added: writing to CSV file.
Usage example: python3 csvfaker.py --rows 5 --output-file people.csv --locale ru_RU name date_of_birth email
"""

import argparse
import csv
import inspect
import sys

import faker

__version__ = '2.0.0'


def provider_methods(provider_method):
    """Validate list of provider methods."""
    fake = faker.Factory.create()
    if provider_method not in dir(fake):
        raise argparse.ArgumentTypeError('Invalid fake {0}. For a list of all available fakes, run with --list-fakes'
                                         .format(provider_method))
    return provider_method


def show_fakes():
    """Show a list of all available provider methods."""
    # TODO: There must be a better way to do that.
    fake = faker.Factory.create()
    fakes = []
    providers_list = fake.get_providers()
    for provider in providers_list:
        provider_list = inspect.getmembers(provider, predicate=inspect.ismethod)
        for method_name, method_value in provider_list:
            if not method_name.startswith('_'):
                fakes.append(method_name)

    """Print a sorted list of unique methods."""
    print(" ".join(sorted(list(set(fakes)))))
    print("")
    print("For details see http://fake-factory.readthedocs.io/en/latest/providers.html")


def main():
    parser = argparse.ArgumentParser(description='Generate a CSV file with fake data.')
    parser.add_argument(metavar="FAKE", dest='methods', type=provider_methods, nargs='*',
                        default=['name', 'job', 'state'],
                        help="The name of the fake(s) to use to generate output, separated by space. Will also be used "
                             "as column headers. If omitted, the fakes 'name job state' will be used.")
    parser.add_argument('-f', '--list-fakes', action='store_true',
                        help='Show a list of all available fakes.')
    parser.add_argument('-r', '--rows', type=int, default=10,
                        help='Number of rows to generate. If omitted it defaults to 10.')
    parser.add_argument('-l', '--locale', type=str,
                        help="Locale to use. Examples: 'en_US', 'es'.")
    parser.add_argument('-s', '--seed', type=str,
                        help="Seed to use. Generated result will be the same when called with the same seed.")
    parser.add_argument('-n', '--no-headers-row', dest='headers', action='store_false',
                        help='Do not output columns headers.')
    parser.add_argument('-p', '--replace-newline', type=str,
                        help='Replace newline character with provided string.')
    parser.add_argument('-d', '--delimiter', type=str, default=',',
                        help='Output column delimiter.')
    parser.add_argument('--version', action='version',
                        version='%(prog)s {version}'.format(version=__version__))
    parser.add_argument('-o', '--output-file', type=str,
                        help="Output file.")
    args = parser.parse_args()

    if args.list_fakes:
        show_fakes()
        exit(0)

    if args.output_file is not None:
        with open(args.output_file, 'w', newline='', encoding='utf8') as csv_file:
            writer = csv.writer(csv_file, delimiter=args.delimiter)
            write_csv(args, writer)
    else:
        writer = csv.writer(sys.stdout, delimiter=args.delimiter)
        write_csv(args, writer)


def write_csv(args, writer):
    if args.headers:
        writer.writerow(args.methods)

    fake = faker.Factory.create(args.locale)
    if args.seed:
        fake.seed(args.seed)
    for r in range(args.rows):
        row = []
        for provider_method in args.methods:
            if sys.version_info >= (3, 0):
                cell = getattr(fake, provider_method)()
            else:
                cell = str(getattr(fake, provider_method)(), 'utf-8')
            if args.replace_newline is not None:
                cell = cell.replace('\n', args.replace_newline).replace('\r', '')
            row.append(cell)
        writer.writerow(row)


if __name__ == "__main__":
    main()

## Introduction

Universal design, as defined by [UniversalDesign.org](https://universaldesign.org/definition):

> Universal Design (UD) can be used as a verb or a noun. When used as a verb, it refers to the process of designing something to be functional as possible for as many people as possible. When used as a noun, it refers to a functionally inclusive design of something. Universal accessibility is the outcome of a successful universal design process.

## General accessibility

#### Visibility and clarity

Using the monochome theme, combined with tables and clearly labelled buttons, the visual clarity of the GUI is good. The texts are short and descriptive, and have been tested on several external test users in order to make sure that information is presented in a short but sufficient manner.

As per design conventions, button labels make use of the ellipsis (…) to communicate that the action advertised by the button will be done in a new dialog that opens when the button is clicked. The lack of ellipsis indicated that the action will be exectued directly when the button is activated. This gives the user a clear message as to what will happen. E.g. the "Add Income …" button indicates that a new dialog will open, while the "Delete" button will delete directly when clicked.

#### Readability

The application show negative numbers with a minus sign in front of the number, e.g. "-230 NOK", to show that the number is indeed negative, without having to rely on differentiation of color.

There's also an optional setting for showing negative numbers in red, as this is a convention in finance and economics in addition to being another way to differetiating negative from positive numbers.

#### Navigation

Keyboard navigation works for the application. This enables those rely on non-mouse input, as well as those who just prefer using keyboards, to interact effectively with the application.

## Localization (L10N) and Internationalization (L18N)

The most lacking part of our universal design and accessibility is in localization and internationalization. The entire application relies on the user being able to understand spoken English (screen reader) and/or written English (GUI). The reason for this is that there's simply not the time nor resources for us to implement more than one language.

The interface also relies heavily on the Left-to-Right (LTR) oritentation natural to those using Latin, Cyrillic, Greek and some Asian writing systems. This coverst most, but not all, potential users of the application. Those used to Right-to-Left (RTL) orientation may not find the arrangement as natural as LTR-users.

## Colors and Contrast

- Dominant colors: Grey, white and black.
- Text colors: Black
- Chart colors, Dashboard: Red and green
- Chart colors, Budget: Red and green
- Negative numbers in red (optional setting)

The application's color scheme mostly features black, white and grey. It was decided to use the default colors supplied by SceneBuilder and JavaFX. While not the most exciting colors, greys, whites and blacks are usually easy to read and contrast against each other. It also lends the application a familiar and professional look, which may be ruined if too much color were to be introduced.

The contrast is inherited from the default theme by SceneBuilder. This style is very readable, as it have a very "standard" and contrasting design with the black-on-lightgrey colors.

Negative numbers are denoted with a "minus" sign in front of the number, with the option to set negatives to be colored red (via Settings).

The only splash of color is the right navigation sidebar, which is colored with "Baecon Green". This area also display the application logo and mascot. This doesn't impact the overall readability of the GUI, other than maybe acting as splash of color, visual fluff or clutter, depending on the sensibilities of the individual user.

## Color Blindness

As mentioned, the application does not feature many colors in the interface. However, since the areas in which there are colors, specifically the graphs and chards in the Dashboard and Budget pages, can be problematic for people with color blindness, there has been implemented a setting to change the colors to be compatible with the most common variety, which is red-green color blindness. Checking the setting will switch the graph colors from the standard green and red to black and grey. This also makes the setting compatible with blue-yellow color blindness

Since grey is a dominant color in our UI, a shade of grey was needed, that would stand out from the background enough to be distinct, but also not being too dark such that it would be hard to distinguis from the black.

User test with a red-green color blind person was performed, to have at least some feedback on the result.

#### Colorblind Mode

The images below show our colorblind mode:

**Default Red-Green pie chart in Budget (1)**, in addition to negative numbers in red. Here, colorblind mode has not been activated.

![final-Budget-01](uploads/a96cf7904f64d40d3682f94b29ef2cf5/final-Budget-01.png)

**The user navigates to the Accessibility section of the Settings and enabled Colorblind Mode via the checkbox (2).**

![uu-02-settgins_Accessibility-01-cbmode-](uploads/12974ab0c209f22957a766f90980270d/uu-02-settgins_Accessibility-01-cbmode-.png)

**The Budget page now display a black and grey pie chart (3).**

![cbMode-02-budget](uploads/df24614a97c0b0addb0f6e1ce834b108/cbMode-02-budget.png)

**The graphs in Dashboard will also change from Red-Green (4) to Black-Grey (5) when the Colorblind Mode setting is selected**

![final-Dashboard-01](uploads/dd3291d27d07da55cb30d91899868a8a/final-Dashboard-01.png)

**Above**: Colorblind mode off.

**Below**: Colorblind mode on.

![cbMode-01-dashboard](uploads/8c485535444b1a2e8e0456868879563b/cbMode-01-dashboard.png)

---

## Screen Reader Compatibility

Testing environment:

| Operating system | Screen reader software |
|------------------|------------------------|
| Kubuntu 22.04 | [Orca Screen Reader](https://help.gnome.org/users/orca/stable/) |
| Mac OS Big Sur | [Apple Mac OS VoiceOver](https://support.apple.com/guide/voiceover/welcome/mac) |
| Windows 11 | [Microsoft Narrator](https://support.microsoft.com/en-us/windows/complete-guide-to-narrator-e4397a0d-ef4f-b386-d8ae-c172f109bdb1#WindowsVersion=Windows_11) |

The application have been made compatible with screen reader applications via the Accessibility tags and texts available in JavaFX SceneBuilder.
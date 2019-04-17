import React, {Component} from 'react';
import PropTypes from 'prop-types';
import TimeAgo from 'react-timeago';

class Story extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
    }

    getHighlightingHTML() {
        return {__html: this.props.highlighting[Object.keys(this.props.highlighting)[0]][0]};

    }

    getURL() {
        if (this.props.data.url) {
            return this.props.data.url;
        } else {
            return this.getHNStoryURL();
        }

    }


    getHNStoryURL() {
        return "https://news.ycombinator.com/item?id=" + this.props.data.id;
    }

    getHNUserURL() {
        return "https://news.ycombinator.com/user?id=" + this.props.data.author;
    }

    getStoryTitle() {
        return {__html: this.stringTruncate((this.props.data.title ? this.props.data.title : this.props.data.text), 200)};
    }

    stringTruncate(str, length){
        var dots = str.length > length ? '...' : '';
        return str.substring(0, length)+dots;
    }

    render() {
        return (
            <div className="card">
                <div className="card-body">
                    <div>
                        <a href={this.getURL()} target="_blank">
                            {"title" in this.props.highlighting ?
                                <h4 dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                                <h4 dangerouslySetInnerHTML={this.getStoryTitle()}/>}
                        </a>
                    </div>
                    <div>
                        <a href={this.getURL()} target="_blank">{this.getURL()}</a>
                    </div>

                    <div>
                        <a href={this.getHNUserURL()} target="_blank">{this.props.data.author}</a> | <a
                        href={this.getHNStoryURL()} target="_blank">{this.props.data.score} points</a> | <a
                        href={this.getHNStoryURL()} target="_blank"><TimeAgo date={this.props.data.time}/></a>
                    </div>

                    <div>
                        {"text" in this.props.highlighting ?
                            <span dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                            this.props.data.text ? <span>{this.props.data.text}</span> : <span/>}
                    </div>
                </div>
            </div>
        );
    }
}

Story.propTypes = {
    data: PropTypes.object.isRequired,
    highlighting: PropTypes.object.isRequired
};

export default Story;